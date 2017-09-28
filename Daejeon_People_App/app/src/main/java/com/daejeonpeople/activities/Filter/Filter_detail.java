package com.daejeonpeople.activities.Filter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.daejeonpeople.R;
import com.daejeonpeople.activities.Detail.Detail;
import com.daejeonpeople.support.views.SnackbarManager;

import java.util.ArrayList;

/**
 * Created by KimDongGyu on 2017-08-31.
 */

public class Filter_detail extends AppCompatActivity {

    private Filter_detail_ItemAdapter adapter;

    private String [][] Tourist = {
                                    {"자연관광지", "국립공원", "도립공원", "군립공원", "산", "자연생태관광지", "자연휴양림", "수목원", "폭포", "계곡", "약수터", "해안절경", "해수욕장", "섬", "항구/포구", "어촌", "등대", "호수", "강", "동굴"},
                                    {"관광자원", "희귀동,식물", "기암괴석"},
                                    {"역사관광지", "고궁", "성", "문", "고택", "생가", "민속마을", "유적지/사적지", "사찰", "종교성지", "안보관광"},
                                    {"휴양관광지", "유원지", "관광단지", "온천/욕장/스파", "이색찜질방", "헬스투어", "테마공원", "공원", "유람선/잠수함관광"},
                                    {"체험관광지", "농,산,어촌 체험", "전통체험", "산사체험", "이색체험", "관광농원", "이색거리"},
                                    {"산업관광지", "제철소", "조선소", "공단", "발전소", "광산", "식음료", "화학/금속", "기타", "전자/반도체", "자동차"},
                                    {"건축/조형물", "다리/대교", "기념탑/기념비/전망대", "분수", "동상", "터널", "유명건물"}
                                };

    private String [][] Events = {
                                    {"축제", "문화관광축제", "일반축제"},
                                    {"공연/행사", "전통공연", "연극", "뮤지컬", "오페라", "전시회", "박람회", "컨벤션", "무용", "클래식음악회", "대중콘서트", "영화", "스포츠경기", "기타행사"}
                                };

    private String [][] Leisure = {
                                    {"레포츠소개", "육상레포츠", "수상레포츠", "항공레포츠"},
                                    {"육상레포츠", "스포츠센터", "수련시설", "경기장", "인라인(실내 인라인 포함)", "자전거하이킹", "카트", "골프", "경마", "경륜", "카지노", "승마", "스키/스노보드", "스케이트", "썰매장", "수렵장", "사격장", "야영장,오토캠핑장", "암벽등반", "빙벽등반", "서바이벌게임", "ATV", "MTB", "오프로드", "번지점프", "자동차경주", "스키(보드)렌탈샵", "트래킹"},
                                    {"수상레포츠", "윈드서핑/제트스키", "카약/카누", "요트", "스노쿨링/스킨스쿠버다이빙", "민물낚시", "바다낚시", "수영", "래프팅"},
                                    {"항공레포츠", "스카이다이빙", "초경량비행", "헹글라이딩/패러글라이딩", "열기구"},
                                    {"복합레포츠", "복합레포츠"}
                                };

    private String [][] Shopping = {
                                    {"쇼핑","5일장", "상설시장", "백화점", "면세점", "할인매장", "전문상가", "공예,공방", "관광기념품점", "특산물판매점"}
                                };
    private String [][] Culture = {
                                    {"문화시설", "박물관", "기념관", "전시관", "컨벤션센터", "미술관/화랑", "공연장", "문화원", "외국문화원", "도서관", "대형서점", "문화전수시설", "영화관", "어학당", "학교"}
                                };
    private String [][] Course = {
                                    {"가족코스", "가족코스"},
                                    {"나홀로코스", "나홀로코스"},
                                    {"힐링코스", "힐링코스"},
                                    {"도보코스", "도보코스"},
                                    {"캠핑코스", "캠핑코스"},
                                    {"맛코스", "맛코스"}
                                };
    private String [][] Accommodation = {
                                            {"숙박시설", "관광호텔", "수상관광호텔", "전통호텔", "가족호텔", "콘도미니엄", "유스호스텔", "펜션", "여관", "모텔", "민박", "게스트하우스", "홈스테이", "서비스드레지던스", "의료관광호텔", "소형호텔", "한옥스테이"}
                                        };
    private String [][] Restaurant = {
                                        {"음식점", "한식", "서양식", "일식", "중식", "아시아식", "패밀리레스토랑", "이색음식점", "채식전문점", "바/까페", "클럽"}
                                    };

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.filter_detail);

        //툴바 생성
        Toolbar toolbar = (Toolbar) findViewById(R.id.filter_detial_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        Filter_detail_CustomRecyclerView recyclerView = (Filter_detail_CustomRecyclerView) findViewById(R.id.filter_recyclerview);

        final Intent intent = getIntent();
        String str =  intent.getStringExtra("key");

        if(str.equals("Tourist")) {
            Log.d("알림", "관광지");
            adapter = new Filter_detail_ItemAdapter(getApplicationContext() ,Tourist);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new Filter_detail_ItemLayoutManger(this));
        } else if(str.equals("Events")) {
            Log.d("알림", "행사 축제");
            adapter = new Filter_detail_ItemAdapter(getApplicationContext(), Events);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new Filter_detail_ItemLayoutManger(this));
        } else if(str.equals("Leisure")) {
            Log.d("알림", "레포츠");
            adapter = new Filter_detail_ItemAdapter(getApplicationContext(), Leisure);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new Filter_detail_ItemLayoutManger(this));
        } else if(str.equals("Shopping")) {
            Log.d("알림", "쇼핑");
            adapter = new Filter_detail_ItemAdapter(getApplicationContext(), Shopping);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new Filter_detail_ItemLayoutManger(this));
        } else if(str.equals("Culture")) {
            Log.d("알림", "문화시설");
            adapter = new Filter_detail_ItemAdapter(getApplicationContext(), Culture);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new Filter_detail_ItemLayoutManger(this));
        } else if(str.equals("Course")) {
            Log.d("알림", "여행코스");
            adapter = new Filter_detail_ItemAdapter(getApplicationContext(), Course);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new Filter_detail_ItemLayoutManger(this));
        } else if(str.equals("Accommodation")) {
            Log.d("알림", "숙박 리조트");
            adapter = new Filter_detail_ItemAdapter(getApplicationContext(), Accommodation);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new Filter_detail_ItemLayoutManger(this));
        } else if(str.equals("Restaurant")) {
            Log.d("알림", "음식점");
            adapter = new Filter_detail_ItemAdapter(getApplicationContext(), Restaurant);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new Filter_detail_ItemLayoutManger(this));
        } else {
            Log.d("알림", "비정상적인 실행");
            Toast.makeText(getApplicationContext(), "비정상적인 실행입니다.", Toast.LENGTH_LONG).show();
        }

        Button filter_back_btn = (Button) findViewById(R.id.filter_detial_backbtn);
        filter_back_btn.setOnClickListener(new Button.OnClickListener() {
            @Override public void onClick(View view) {
                finish();
            }
        }) ;
    }
}