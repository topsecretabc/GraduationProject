package com.jeff.footballmanager.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;

import com.jeff.footballmanager.service.ImageInfo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.widget.ImageView;

/**
 * 图片三级缓存。
 * 1.从内存中取。
 * 2.内存不存在此图片，从硬盘中取。
 * 3.硬盘中不存在，从网络获取图片。
 * @author dengjifu
 *
 */
public class ImageCacheUtils {
	
	private static LruCache<String, Bitmap> mCache;
	private static String path;
	
	private static String FOLDER_PATH = "/img";
	private ImageView imageView;
	private Context context;
	private Bitmap map=null;
	
	//初始化LruCache
	public ImageCacheUtils(Context context){
		this.context = context;
		
		if(path==null){

			File file = null;
			if(ExistSDCard()){
				file = new File(Environment.getExternalStorageDirectory()+FOLDER_PATH);
				if(!file.exists()){
					try {
						file.mkdir();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}else{
				file = new File(context.getFilesDir()+FOLDER_PATH);
				if(!file.exists()){
					file.mkdir();
				}
			}
			path = file.getAbsolutePath();
			Log.i("jeff",path);
		}
		
		if (mCache == null) {
            // 最大使用的内存空间
            int maxSize = (int) (Runtime.getRuntime().freeMemory() / 4);
            mCache = new LruCache<String, Bitmap>(maxSize){
            	//必须重写此方法，来测量Bitmap的大小  
                @Override  
                protected int sizeOf(String key, Bitmap value) {  
                    return value.getRowBytes() * value.getHeight();  
                }
            };
        }
	}
	
	public Bitmap getBitmapFromCache(String http){
		String jpgName = getNameFromHttp(http);
		Bitmap map = mCache.get(jpgName);
		return map;
	}
	
	public Bitmap getBitMapFromLocal(String http){
		Bitmap map= null;
		File file = null;
		InputStream is = null;
        try {
        	file = new File(path,getNameFromHttp(http));
        	is = new FileInputStream(file);
        	map =  BitmapFactory.decodeStream(is);
        } catch (Exception e) {
        }finally{
    		try {
            	if(is!=null)
            		is.close();
			} catch (IOException e) {
			}
        }
		return map;
	}
	
	public void saveBitmap(Bitmap map,String http){
		if(http!=null && map!=null){
			String jpgName = getNameFromHttp(http);
			//保存图片到内存
			mCache.put(jpgName,map);
			//保存图片到本地磁盘
			saveBitMapToLocal(jpgName,map);
		}
	}

	public void saveBitMapToLocal(String name,Bitmap map) {
		File file = new File(path,name);
		OutputStream os = null;
		try {
			os = new FileOutputStream(file);
			map.compress(CompressFormat.JPEG,100,os);
		} catch (FileNotFoundException e) {
			Log.i("jeff","存储图片失败！");
		}finally{
			try {
				if(os!=null)
					os.close();
			} catch (IOException e) {
			}
		}
	}

	/**
	 * 是否有sd卡
	 * @return
	 */
    private boolean ExistSDCard() {  
        if (android.os.Environment.getExternalStorageState().equals(  
          android.os.Environment.MEDIA_MOUNTED)) {  
         return true;  
        } else  
         return false;  
   }  
    
	public String getNameFromHttp(String http){
		int index = http.lastIndexOf("/");
		String name = http.substring(index+1, http.length()); 
		return name;
	}
	
	//显示图片
	public Bitmap disPlayImage(String http){
		if(getBitmapFromCache(http)!=null)
			return getBitmapFromCache(http);
		if(getBitMapFromLocal(http)!=null)
			return getBitMapFromLocal(http);
		else
			return null;
	}
	
	public static void clearLocalBitmap(double precent){
		ArrayList<ImageInfo> list = new ArrayList<ImageInfo>();
		File file = new File(path);
		if(file.isDirectory()){
			File[] files = file.listFiles();
			ImageInfo imageInfo ;
			for(File f:files){
				imageInfo = new ImageInfo();
				imageInfo.setModifyTime(f.lastModified());
				imageInfo.setPath(f.getPath());
				list.add(imageInfo);
			}
		}
		Collections.sort(list);
		
		for(int i=0,j=list.size();i<j*precent;i++){
			deleteFile(list.get(i).getPath());
		}
	}
	
	private static void deleteFile(String path2) {
		File file = new File(path2);
		if(file.exists())
			file.delete();
	}

	public static int getFolderSize(){
		int size = 0;
		File file = new File(path);
		if(file.isDirectory()){
			File[] files = file.listFiles();
			for(File f:files){
				size += getFileSize(f.getPath());
			}
		}
		return size;
	}
	
	@SuppressWarnings("resource")
	public static int getFileSize(String path){
		File file = new File(path);
		InputStream is = null;
		int size = 0;
		try {
			is = new FileInputStream(file);
			size = is.available();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return size;
	}
	
}
