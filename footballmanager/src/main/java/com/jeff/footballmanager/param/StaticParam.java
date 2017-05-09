package com.jeff.footballmanager.param;

public class StaticParam {
	
	public static final int MESSAGE_SHOW = 0;
	public static final int MESSAGE_SHOW_REFRESH = 1;
	public static final int MESSAGE_ERROR = 11;

    public static String USER_NAME = "";
	public static String USER_NO = "";
	public static String HEAD_PATH = "";
	public static String BG_PATH = "";
	public static String LOGIN_TYPE = "common";

	//新闻api
	public static final String FOOTBALL_URL = "http:123.57.251.89:8080/footballmanager/";
	public static final String NEWS_URL = "http://api.dagoogle.cn/news/get-news?justList=1";
	public static final String NEWS_DETAILS_URL = "http://api.dagoogle.cn/news/single-news?";

	//自定义访问API令牌
	public static final String API_TOKEN = "?api_token=jefffootballmanager201707";

	//API操作数据详细接口
	public static final String API = "http://123.57.251.89:8080/footballmanager/api";
	public static final String API_LOGIN = "http://123.57.251.89:8080/footballmanager/api/toLogin.do";
	public static final String API_GET_ALL_PLAYER = "/getAllPlayer.do";
	public static final String API_ADD_PLAYER = "/toAddPlayer.do";
	public static final String API_DELETE_PLAYER = "/toDeletePlayer.do";
	public static final String API_GET_ALL_TEAM = "/getAllTeam.do";
	public static final String API_ADD_TEAM = "/toAddTeam.do";
	public static final String API_DELETE_TEAM = "/toDeleteTeam.do";
	public static final String API_GET_ALL_COMPETITION = "/getAllCompetition.do";
	public static final String API_DELETE_COMPETITION = "/toDeleteCompetition.do";
	public static final String API_ADD_COMPETITION = "/toAddCompetition.do";
	public static final String API_GET_ALL_TECHO = "/getAllPlayer.do";
	//修改密码
	public static final String API_TO_GET_PSD = "/toGetPsd.do";
	//忘记密码
	public static final String API_TO_FORGET_PSD = "/toGetMyPsd.do";

	//普通用户注册及第三方登录自动注册
	public static final String API_TO_REGISTER = "/toRegister.do";
	public static final String API_TO_QQ_REGISTER = "/toQQRegister.do";

	public static final String API_GET_PLAYER_BY_TEAMNAME = "/getPlayerByTeamName.do";
	//技术统计API
	public static final String API_TECHNO_ASSITING = "/toTechnologyCount.do";
	public static final String API_TECHNO_HIGH = "/toTechnologyCountHigh.do";
	public static final String API_TECHNO_HISTORY_ASSITING = "/toTechnologyHistoryCount.do";
	public static final String API_TECHNO_HISTORY_HIGHT = "/toTechnologyHistoryCountHigh.do";
	public static final String API_TECHNO_TEAM_RANK = "/toTeamRank.do";
	
	public static final String API_GET_PALYER_BY_NO = "/getPlayerByNo.do";
	public static final String API_GET_TEAM_BY_NO = "/getTeamByNo.do";
	public static final String API_GET_COMPETITION_BY_NO = "/getCompetitionByNo.do";
	public static final String API_EDIT_PALYER_BY_NO = "/toEditPlayerByNo.do";
	public static final String API_EDIT_TEAM_BY_NO = "/toEditTeamByNo.do";
	public static final String API_EDIT_COMPETITION_BY_NO = "/toEditCompetitionByNo.do";
	
	//返回参数
	public static final String SUCCESS ="success";
	public static final String FAULT ="fault";

	//第三方接口
	public static final String QQ_APP_ID = "1105990229";
	public static final String QQ_KEY = "6y4iBnd3zoOfDYHH";
	public static final String WX_APP_ID = "wx99b9d1eed20f4feb";//wxd930ea5d5a258f4f

	public static final String SECRET = "0c1d1ae20a41e6e3761d2b661cc957f1";

}

