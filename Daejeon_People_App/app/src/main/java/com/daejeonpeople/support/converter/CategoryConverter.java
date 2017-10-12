package com.daejeonpeople.support.converter;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by JoMingyu on 2017-06-20.
 */

public class CategoryConverter {
    private static Map<String, String> categoryMap = new HashMap<>();

    public String convert(String categoryCode) {
        if(categoryMap.containsKey(categoryCode)) {
            return categoryMap.get(categoryCode);
        } else {
            return "분류 없음";
        }
    }

    static {
        // 관광지
        categoryMap.put("A01010100", "국립공원");
        categoryMap.put("A01010200", "도립공원");
        categoryMap.put("A01010300", "군립공원");
        categoryMap.put("A01010400", "산");
        categoryMap.put("A01010500", "자연생태관광지");
        categoryMap.put("A01010600", "자연휴양림");
        categoryMap.put("A01010700", "수목원");
        categoryMap.put("A01010800", "폭포");
        categoryMap.put("A01010900", "계곡");
        categoryMap.put("A01011000", "약수터");
        categoryMap.put("A01011100", "해안절경");
        categoryMap.put("A01011200", "해수욕장");
        categoryMap.put("A01011300", "섬");
        categoryMap.put("A01011400", "항구/포구");
        categoryMap.put("A01011500", "어촌");
        categoryMap.put("A01011600", "등대");
        categoryMap.put("A01011700", "호수");
        categoryMap.put("A01011800", "강");
        categoryMap.put("A01011900", "동굴");
        categoryMap.put("A01020100", "희귀동/식물");
        categoryMap.put("A01020200", "기암괴석");
        categoryMap.put("A02010100", "고궁");
        categoryMap.put("A02010200", "성");
        categoryMap.put("A02010300", "문");
        categoryMap.put("A02010400", "고택");
        categoryMap.put("A02010500", "생가");
        categoryMap.put("A02010600", "민속마을");
        categoryMap.put("A02010700", "유적지/사적지");
        categoryMap.put("A02010800", "사찰");
        categoryMap.put("A02010900", "종교성지");
        categoryMap.put("A02011000", "안보관광");
        categoryMap.put("A02020100", "유원지");
        categoryMap.put("A02020200", "관광단지");
        categoryMap.put("A02020300", "온천/욕장/스파");
        categoryMap.put("A02020400", "이색찜질방");
        categoryMap.put("A02020500", "헬스투어");
        categoryMap.put("A02020600", "테마공원");
        categoryMap.put("A02020700", "공원");
        categoryMap.put("A02020800", "유람선/잠수함관광");
        categoryMap.put("A02030100", "농/산/어촌 체험");
        categoryMap.put("A02030200", "전통체험");
        categoryMap.put("A02030300", "산사체험");
        categoryMap.put("A02030400", "이색체험");
        categoryMap.put("A02030500", "관광농원");
        categoryMap.put("A02030600", "이색거리");
        categoryMap.put("A02040100", "제철소");
        categoryMap.put("A02040200", "조선소");
        categoryMap.put("A02040300", "공단");
        categoryMap.put("A02040400", "발전소");
        categoryMap.put("A02040500", "광산");
        categoryMap.put("A02040600", "식음료");
        categoryMap.put("A02040700", "화학/금속");
        categoryMap.put("A02040800", "기타");
        categoryMap.put("A02040900", "전자/반도체");
        categoryMap.put("A02041000", "자동차");
        categoryMap.put("A02050100", "다리/대교");
        categoryMap.put("A02050200", "기념탑/기념비/전망대");
        categoryMap.put("A02050300", "분수");
        categoryMap.put("A02050400", "동상");
        categoryMap.put("A02050500", "터널");
        categoryMap.put("A02050600", "유명건물");

        // 문화시설
        categoryMap.put("A02060100", "박물관");
        categoryMap.put("A02060200", "기념관");
        categoryMap.put("A02060300", "전시관");
        categoryMap.put("A02060400", "컨벤션센터");
        categoryMap.put("A02060500", "미술관/화랑");
        categoryMap.put("A02060600", "공연장");
        categoryMap.put("A02060700", "문화원");
        categoryMap.put("A02060800", "외국문화원");
        categoryMap.put("A02060900", "도서관");
        categoryMap.put("A02061000", "대형서점");
        categoryMap.put("A02061100", "문화전수시설");
        categoryMap.put("A02061200", "영화관");
        categoryMap.put("A02061300", "어학당");
        categoryMap.put("A02061400", "학교");

        // 축제/공연/행사
        categoryMap.put("A02070100", "문화관광축제");
        categoryMap.put("A02070200", "일반축제");
        categoryMap.put("A02080100", "전통공연");
        categoryMap.put("A02080200", "연극");
        categoryMap.put("A02080300", "뮤지컬");
        categoryMap.put("A02080400", "오페라");
        categoryMap.put("A02080500", "전시회");
        categoryMap.put("A02080600", "박람회");
        categoryMap.put("A02080700", "컨벤션");
        categoryMap.put("A02080800", "무용");
        categoryMap.put("A02080900", "클래식음악회");
        categoryMap.put("A02081000", "대중콘서트");
        categoryMap.put("A02081100", "영화");
        categoryMap.put("A02081200", "스포츠경기");
        categoryMap.put("A02081300", "기타행사");

        // 여행코스
        categoryMap.put("C01120001", "가족코스");
        categoryMap.put("C01130001", "나홀로코스");
        categoryMap.put("C01140001", "힐링코스");
        categoryMap.put("C01150001", "도보코스");
        categoryMap.put("C01160001", "캠핑코스");
        categoryMap.put("C01170001", "맛코스");

        // 레포츠
        categoryMap.put("A03010100", "육상레포츠");
        categoryMap.put("A03010200", "수상레포츠");
        categoryMap.put("A03010300", "항공레포츠");
        categoryMap.put("A03020100", "스포츠센터");
        categoryMap.put("A03020200", "수련시설");
        categoryMap.put("A03020300", "경기장");
        categoryMap.put("A03020400", "인라인");
        categoryMap.put("A03020500", "자전거하이킹");
        categoryMap.put("A03020600", "카트");
        categoryMap.put("A03020700", "골프");
        categoryMap.put("A03020800", "경마");
        categoryMap.put("A03020900", "경륜");
        categoryMap.put("A03021000", "카지노");
        categoryMap.put("A03021100", "승마");
        categoryMap.put("A03021200", "스키/스노보드");
        categoryMap.put("A03021300", "스케이트");
        categoryMap.put("A03021400", "썰매장");
        categoryMap.put("A03021500", "수렵장");
        categoryMap.put("A03021600", "사격장");
        categoryMap.put("A03021700", "야영장/오토캠핑장");
        categoryMap.put("A03021800", "암벽등반");
        categoryMap.put("A03021900", "빙벽등반");
        categoryMap.put("A03022000", "서바이벌게임");
        categoryMap.put("A03022100", "ATV");
        categoryMap.put("A03022200", "MTB");
        categoryMap.put("A03022300", "오프로드");
        categoryMap.put("A03022400", "번지점프");
        categoryMap.put("A03022500", "자동차경주");
        categoryMap.put("A03022600", "스키(보드) 렌탈샵");
        categoryMap.put("A03022700", "트래킹");
        categoryMap.put("A03030100", "윈드서핑/제트스키");
        categoryMap.put("A03030200", "카약/카누");
        categoryMap.put("A03030300", "요트");
        categoryMap.put("A03030400", "스노쿨링/스킨스쿠버 다이빙");
        categoryMap.put("A03030500", "민물낚시");
        categoryMap.put("A03030600", "바다낚시");
        categoryMap.put("A03030700", "수영");
        categoryMap.put("A03030800", "래프팅");
        categoryMap.put("A03040100", "스카이다이빙");
        categoryMap.put("A03040200", "초경량비행");
        categoryMap.put("A03040300", "행글라이딩/패러글라이딩");
        categoryMap.put("A03040400", "열기구");
        categoryMap.put("A03050100", "복합 레포츠");

        // 숙박
        categoryMap.put("B02010100", "관광호텔");
        categoryMap.put("B02010200", "수상관광호텔");
        categoryMap.put("B02010300", "전통호텔");
        categoryMap.put("B02010400", "가족호텔");
        categoryMap.put("B02010500", "콘도미니엄");
        categoryMap.put("B02010600", "유스호스텔");
        categoryMap.put("B02010700", "펜션");
        categoryMap.put("B02010800", "여관");
        categoryMap.put("B02010900", "모텔");
        categoryMap.put("B02011000", "민박");
        categoryMap.put("B02011100", "게스트하우스");
        categoryMap.put("B02011200", "홈스테이");
        categoryMap.put("B02011300", "서비스드레지던스");
        categoryMap.put("B02011400", "의료관광호텔");
        categoryMap.put("B02011500", "소형호텔");
        categoryMap.put("B02011600", "한옥스테이");

        // 쇼핑
        categoryMap.put("A04010100", "5일장");
        categoryMap.put("A04010200", "상설시장");
        categoryMap.put("A04010300", "백화점");
        categoryMap.put("A04010400", "면세점");
        categoryMap.put("A04010500", "할인매장");
        categoryMap.put("A04010600", "전문상가");
        categoryMap.put("A04010700", "공예/공방");
        categoryMap.put("A04010800", "관광기념품점");
        categoryMap.put("A04010900", "특산물판매점");

        // 음식
        categoryMap.put("A05020100", "한식");
        categoryMap.put("A05020200", "서양식");
        categoryMap.put("A05020300", "일식");
        categoryMap.put("A05020400", "중식");
        categoryMap.put("A05020500", "아시아식");
        categoryMap.put("A05020600", "패밀리레스토랑");
        categoryMap.put("A05020700", "이색음식점");
        categoryMap.put("A05020800", "채식전문점");
        categoryMap.put("A05020900", "바/카페");
        categoryMap.put("A05021000", "클럽럽");
   }
}
