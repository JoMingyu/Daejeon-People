package com.daejeonpeople.activities.Category;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.daejeonpeople.R;

/**
 * Created by KimDongGyu on 2017-08-31.
 */

public class Category_detail extends AppCompatActivity {

    private Category_detail_Adapter adapter;

    //서비스 이름
    private String[][] Tourist = {
            {"자연관광지", "국립공원", "도립공원", "군립공원", "산", "자연생태관광지", "자연휴양림", "수목원", "폭포", "계곡", "약수터", "해안절경", "해수욕장", "섬", "항구/포구", "어촌", "등대", "호수", "강", "동굴"},
            {"관광자원", "희귀동,식물", "기암괴석"},
            {"역사관광지", "고궁", "성", "문", "고택", "생가", "민속마을", "유적지/사적지", "사찰", "종교성지", "안보관광"},
            {"휴양관광지", "유원지", "관광단지", "온천/욕장/스파", "이색찜질방", "헬스투어", "테마공원", "공원", "유람선/잠수함관광"},
            {"체험관광지", "농,산,어촌 체험", "전통체험", "산사체험", "이색체험", "관광농원", "이색거리"},
            {"산업관광지", "제철소", "조선소", "공단", "발전소", "광산", "식음료", "화학/금속", "기타", "전자/반도체", "자동차"},
            {"건축/조형물", "다리/대교", "기념탑/기념비/전망대", "분수", "동상", "터널", "유명건물"}
    };

    private String[][] Events = {
            {"축제", "문화관광축제", "일반축제"},
            {"공연/행사", "전통공연", "연극", "뮤지컬", "오페라", "전시회", "박람회", "컨벤션", "무용", "클래식음악회", "대중콘서트", "영화", "스포츠경기", "기타행사"}
    };

    private String[][] Leisure = {
            {"레포츠소개", "육상레포츠", "수상레포츠", "항공레포츠"},
            {"육상레포츠", "스포츠센터", "수련시설", "경기장", "인라인(실내 인라인 포함)", "자전거하이킹", "카트", "골프", "경마", "경륜", "카지노", "승마", "스키/스노보드", "스케이트", "썰매장", "수렵장", "사격장", "야영장,오토캠핑장", "암벽등반", "빙벽등반", "서바이벌게임", "ATV", "MTB", "오프로드", "번지점프", "자동차경주", "스키(보드)렌탈샵", "트래킹"},
            {"수상레포츠", "윈드서핑/제트스키", "카약/카누", "요트", "스노쿨링/스킨스쿠버다이빙", "민물낚시", "바다낚시", "수영", "래프팅"},
            {"항공레포츠", "스카이다이빙", "초경량비행", "헹글라이딩/패러글라이딩", "열기구"},
            {"복합레포츠", "복합레포츠"}
    };

    private String[][] Shopping = {
            {"쇼핑","5일장", "상설시장", "백화점", "면세점", "할인매장", "전문상가", "공예,공방", "관광기념품점", "특산물판매점"}
    };
    private String[][] Culture = {
            {"문화시설", "박물관", "기념관", "전시관", "컨벤션센터", "미술관/화랑", "공연장", "문화원", "외국문화원", "도서관", "대형서점", "문화전수시설", "영화관", "어학당", "학교"}
    };
    private String[][] Course = {
            {"가족코스", "가족코스"},
            {"나홀로코스", "나홀로코스"},
            {"힐링코스", "힐링코스"},
            {"도보코스", "도보코스"},
            {"캠핑코스", "캠핑코스"},
            {"맛코스", "맛코스"}
    };
    private String[][] Accommodation = {
            {"숙박시설", "관광호텔", "수상관광호텔", "전통호텔", "가족호텔", "콘도미니엄", "유스호스텔", "펜션", "여관", "모텔", "민박", "게스트하우스", "홈스테이", "서비스드레지던스", "의료관광호텔", "소형호텔", "한옥스테이"}
    };
    private String[][] Restaurant = {
            {"음식점", "한식", "서양식", "일식", "중식", "아시아식", "패밀리레스토랑", "이색음식점", "채식전문점", "바/까페", "클럽"}
    };


    //서비스 분류 코드
    private String[][] TouristCode = {
            {"A0101", "A01010100", "A01010200", "A01010300", "A01010400", "A01010500", "A01010600", "A01010700", "A01010800", "A01010900", "A01011000", "A01011100", "A01011200", "A01011300", "A01011400", "A01011500", "A01011600", "A01011700", "A01011800", "A01011900"},
            {"A0102", "A01020100", "A01020200"},
            {"A0201", "A02010100", "A02010200", "A02010300", "A02010400", "A02010500", "A02010600", "A02010700", "A02010800", "A02010900", "A02011000"},
            {"A0202", "A02020100", "A02020200", "A02020300", "A02020400", "A02020500", "A02020600", "A02020700", "A02020800"},
            {"A0203", "A02030100", "A02030200", "A02030300", "A02030400", "A02030500", "A02030600"},
            {"A0204", "A02040100", "A02040200", "A02040300", "A02040400", "A02040500", "A02040600", "A02040700", "A02040800", "A02040900", "A02041000"},
            {"A0205", "A02050100", "A02050200", "A02050300", "A02050400", "A02050500", "A02050600"}
    };

    private String[][] EventsCode = {
            {"A0207", "A02070100", "A02070200"},
            {"A0208", "A02080100", "A02080200", "A02080300", "A02080400", "A02080500", "A02080600", "A02080700", "A02080800", "A02080900", "A02081000", "A02081100", "A02081200", "A02081300"}
    };

    private String[][] LeisureCode = {
            {"A0301", "A03010100", "A03010200", "A03010300"},
            {"A0302", "A03020100", "A03020200", "A03020300", "A03020400", "A03020500", "A03020600", "A03020700", "A03020800", "A03020900", "A03021000", "A03021100", "A03021200", "A03021300", "A03021400", "A03021500", "A03021600", "A03021700", "A03021800", "A03021900", "A03022000", "A03022100", "A03022200", "A03022300", "A03022400", "A03022500", "A03022600", "A03022700"},
            {"A0303", "A03030100", "A03030200", "A03030300", "A03030400", "A03030500", "A03030600", "A03030700", "A03030800"},
            {"A0304", "A03040100", "A03040200", "A03040300", "A03040400"},
            {"A0305", "A03050100"}
    };

    private String[][] ShoppingCode = {
            {"A0401","A04010100", "A04010200", "A04010300", "A04010400", "A04010500", "A04010600", "A04010700", "A04010800", "A04010900"}
    };
    private String[][] CultureCode = {
            {"A0206", "A02060100", "A02060200", "A02060300", "A02060400", "A02060500", "A02060600", "A02060700", "A02060800", "A02060900", "A02061000", "A02061100", "A02061200", "A02061300", "A02061400"}
    };
    private String[][] CourseCode = {
            {"C0112", "C01120001"},
            {"C0113", "C01130001"},
            {"C0114", "C01140001"},
            {"C0115", "C01150001"},
            {"C0116", "C01160001"},
            {"C0117", "C01170001"}
    };
    private String[][] AccommodationCode = {
            {"B0201", "B02010100", "B02010200", "B02010300", "B02010400", "B02010500", "B02010600", "B02010700", "B02010800", "B02010900", "B02011000", "B02011100", "B02011200", "B02011300", "B02011400", "B02011500", "B02011600"}
    };
    private String[][] RestaurantCode = {
            {"A0502", "A05020100", "A05020200", "A05020300", "A05020400", "A05020500", "A05020600", "A05020700", "A05020800", "A05020900", "A05021000"}
    };

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.filter_detail);

        //툴바 생성
        Toolbar toolbar = (Toolbar) findViewById(R.id.filter_detial_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.filter_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        RecyclerView.ItemAnimator animator = recyclerView.getItemAnimator();
        if (animator instanceof DefaultItemAnimator) {
            ((DefaultItemAnimator) animator).setSupportsChangeAnimations(false);
        }

        final Intent intent = getIntent();
        String str =  intent.getStringExtra("key");

        if(str.equals("Tourist")) {
            Log.d("알림", "관광지");
            Category_detail_Data_Factory category_detail_data_factory = new Category_detail_Data_Factory(Tourist, TouristCode);
            adapter = new Category_detail_Adapter(category_detail_data_factory.makeData());
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);
        } else if(str.equals("Events")) {
            Log.d("알림", "행사 축제");
            Category_detail_Data_Factory category_detail_data_factory = new Category_detail_Data_Factory(Events, EventsCode);
            adapter = new Category_detail_Adapter(category_detail_data_factory.makeData());
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);
        } else if(str.equals("Leisure")) {
            Log.d("알림", "레포츠");
            Category_detail_Data_Factory category_detail_data_factory = new Category_detail_Data_Factory(Leisure, LeisureCode);
            adapter = new Category_detail_Adapter(category_detail_data_factory.makeData());
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);
        } else if(str.equals("Shopping")) {
            Log.d("알림", "쇼핑");
            Category_detail_Data_Factory category_detail_data_factory = new Category_detail_Data_Factory(Shopping, ShoppingCode);
            adapter = new Category_detail_Adapter(category_detail_data_factory.makeData());
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);
        } else if(str.equals("Culture")) {
            Log.d("알림", "문화시설");
            Category_detail_Data_Factory category_detail_data_factory = new Category_detail_Data_Factory(Culture, CultureCode);
            adapter = new Category_detail_Adapter(category_detail_data_factory.makeData());
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);
        } else if(str.equals("Course")) {
            Log.d("알림", "여행코스");
            Category_detail_Data_Factory category_detail_data_factory = new Category_detail_Data_Factory(Course, CourseCode);
            adapter = new Category_detail_Adapter(category_detail_data_factory.makeData());
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);
        } else if(str.equals("Accommodation")) {
            Log.d("알림", "숙박 리조트");
            Category_detail_Data_Factory category_detail_data_factory = new Category_detail_Data_Factory(Accommodation, AccommodationCode);
            adapter = new Category_detail_Adapter(category_detail_data_factory.makeData());
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);
        } else if(str.equals("Restaurant")) {
            Log.d("알림", "음식점");
            Category_detail_Data_Factory category_detail_data_factory = new Category_detail_Data_Factory(Restaurant, RestaurantCode);
            adapter = new Category_detail_Adapter(category_detail_data_factory.makeData());
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);
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

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        adapter.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        adapter.onRestoreInstanceState(savedInstanceState);
    }
}