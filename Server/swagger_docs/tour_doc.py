SEARCHED_TOUR_LIST = {
    'tags': ['여행지 리스트'],
    'description': '여행지 리스트 - 검색(JWT Required)',
    'parameters': [
        {
            'name': 'keyword',
            'description': '검색 키워드',
            'in': 'query',
            'type': 'str'
        },
        {
            'name': 'sort_type',
            'description': '정렬 타입(1: 조회순, 2: 위시리스트 많은 순, 3: 거리순)',
            'in': 'query',
            'type': 'int'
        }
    ],
    'responses': {
        '200': {
            'description': '검색 결과 있음',
            'examples': {
                'application/json': [
                    {
                        "content_id": 1906715,
                        "content_type_id": 15,
                        "title": "금강로하스축제 2017",
                        "address": "대전광역시 대덕구 미호동 57-1",
                        "category": "A02070200",
                        "image": None,
                        "wish_count": 0,
                        "wished": False,
                        "x": 127.4738011851,
                        "y": 36.47306428
                    },
                    {
                        "content_id": 2407303,
                        "content_type_id": 12,
                        "title": "금강로하스대청공원",
                        "address": "대전광역시 대덕구 대청로 607",
                        "category": "A02020700",
                        "image": "http://tong.visitkorea.or.kr/cms/resource/74/2405174_image2_1.jpg",
                        "wish_count": 0,
                        "wished": False,
                        "x": 127.4735567469,
                        "y": 36.4736225451
                    }
                ]
            }
        },
        '204': {
            'description': '검색 결과 없음'
        }
    }
}

CATEGORIZED_TOUR_LIST = {
    'tags': ['여행지 리스트'],
    'description': '여행지 리스트 - 카테고리 필터링(JWT Required)',
    'parameters': [
        {
            'name': 'category',
            'description': '필터링할 카테고리(소분류)',
            'in': 'query',
            'type': 'str'
        },
        {
            'name': 'sort_type',
            'description': '정렬 타입(1: 조회순, 2: 위시리스트 많은 순, 3: 거리순)',
            'in': 'query',
            'type': 'int'
        }
    ],
    'responses': {
        '200': {
            'description': '검색 결과 있음',
            'examples': {
                'application/json': [
                    {
                        "content_id": 1257550,
                        "content_type_id": 15,
                        "title": "계족산맨발축제 2017",
                        "address": "대전광역시 대덕구 장동 485",
                        "category": "A02070200",
                        "image": None,
                        "wish_count": 0,
                        "wished": False,
                        "x": 127.4444556688,
                        "y": 36.4050965379
                    },
                    {
                        "content_id": 1072689,
                        "content_type_id": 15,
                        "title": "견우직녀축제 2017",
                        "address": "대전광역시 서구 둔산대로 155",
                        "category": "A02070200",
                        "image": None,
                        "wish_count": 0,
                        "wished": False,
                        "x": 127.3879727183,
                        "y": 36.3725548127
                    },
                    {
                        "content_id": 1906715,
                        "content_type_id": 15,
                        "title": "금강로하스축제 2017",
                        "address": "대전광역시 대덕구 미호동 57-1",
                        "category": "A02070200",
                        "image": None,
                        "wish_count": 0,
                        "wished": False,
                        "x": 127.4738011851,
                        "y": 36.47306428
                    }
                ]
            }
        },
        '204': {
            'description': '검색 결과 없음'
        }
    }
}

TOUR_DETAIL = {
    'tags': ['여행지 세부 정보'],
    'description': '여행지 세부 정보 조회(JWT Required)',
    'parameters': [
        {
            'name': 'content_id',
            'description': '세부 정보를 조회할 content id',
            'in': 'query',
            'type': 'int'
        }
    ],
    'responses': {
        '200': {
            'description': '세부 정보 조회 성공',
            'examples': {
                'application/json(content type id 12)': {
                    "content_id": 1622646,
                    "content_type_id": 12,
                    "title": "가양비래근린공원",
                    "address": "대전광역시 대덕구 신상로 65",
                    "x": 127.4593639622,
                    "y": 36.3576077922,
                    "tel": None,
                    "image": None,
                    "wish_count": 0,
                    "images": [
                        "http://tong.visitkorea.or.kr/cms/resource/00/1585300_image2_1.jpg",
                        "http://tong.visitkorea.or.kr/cms/resource/01/1585301_image2_1.jpg"
                    ],
                    "credit_card": "없음",
                    "baby_carriage": "불가",
                    "pet": "없음",
                    "use_time": None,
                    "rest_date": None,
                    "wished": True,
                    "category": "A02020700"
                },
                'application/json(content type id 14)': {
                    "content_id": 129785,
                    "content_type_id": 14,
                    "title": "국립중앙과학관",
                    "address": "대전광역시 유성구 대덕대로 481",
                    "x": 127.3751904492,
                    "y": 36.3754709184,
                    "tel": None,
                    "image": "http://tong.visitkorea.or.kr/cms/resource/23/1092923_image2_1.jpg",
                    "wish_count": 0,
                    "images": [
                        "http://tong.visitkorea.or.kr/cms/resource/29/1092829_image2_1.jpg",
                        "http://tong.visitkorea.or.kr/cms/resource/30/1092830_image2_1.jpg",
                    ],
                    "credit_card": "가능",
                    "baby_carriage": "없음",
                    "pet": "불가",
                    "use_time": "09:30~17:50",
                    "rest_date": "매주 월요일,법정공휴일 다음날 휴관,명절(신정,설,추석) 당일 및 연휴 다음날 휴관",
                    "use_fee": None,
                    "spend_time": None,
                    "wished": False,
                    "category": "A02060300"
                },
                'application/json(content type id 15)': {
                    "content_id": 1072689,
                    "content_type_id": 15,
                    "title": "견우직녀축제 2017",
                    "address": "대전광역시 서구 둔산대로 155",
                    "x": 127.3879727183,
                    "y": 36.3725548127,
                    "tel": "042-330-3915",
                    "image": "None",
                    "wish_count": 0,
                    "images": [],
                    "start_date": "20170826",
                    "end_date": "20170827",
                    "use_fee": "",
                    "spend_time": "기간내자유",
                    "wished": False,
                    "category": "A02070200"
                },
                'application/json(content type id 25)': {
                    "content_id": 1978169,
                    "content_type_id": 25,
                    "title": "국립현충원과 대전의 문화유산을 살펴볼 기회",
                    "address": None,
                    "x": 127.3355089264,
                    "y": 36.337157932,
                    "tel": None,
                    "image": None,
                    "wish_count": 0,
                    "images": [],
                    "spend_time": "6시간",
                    "distance": "33.31km",
                    "wished": False,
                    "category": "C01120001"
                },
                'application/json(content type id 28)': {
                    "content_id": 1069926,
                    "content_type_id": 28,
                    "title": "대전 자전거 [갑천 순환 코스]",
                    "address": "대전광역시 유성구 대덕대로",
                    "x": 127.3880199345,
                    "y": 36.3726214725,
                    "tel": "042-1899-2282",
                    "image": "http://tong.visitkorea.or.kr/cms/resource/84/607684_image2_1.jpg",
                    "wish_count": 0,
                    "images": [
                        "http://tong.visitkorea.or.kr/cms/resource/90/607690_image2_1.jpg",
                        "http://tong.visitkorea.or.kr/cms/resource/94/607694_image2_1.jpg"
                    ],
                    "credit_card": "없음",
                    "baby_carriage": "없음",
                    "pet": "없음",
                    "use_time": None,
                    "rest_date": None,
                    "use_fee": None,
                    "wished": False,
                    "category": "A03020500"
                },
                'application/json(content type id 32)': {
                    "content_id": 138836,
                    "content_type_id": 32,
                    "title": "까펠라모텔",
                    "address": "대전광역시 유성구 한밭대로492번길 16-32",
                    "x": 127.3514708025,
                    "y": 36.3587612986,
                    "tel": "042-823-0953",
                    "image": "http://tong.visitkorea.or.kr/cms/resource/47/1945947_image2_1.jpg",
                    "wish_count": 0,
                    "images": [
                        "http://tong.visitkorea.or.kr/cms/resource/45/1945945_image2_1.jpg",
                        "http://tong.visitkorea.or.kr/cms/resource/46/1945946_image2_1.jpg",
                        "http://tong.visitkorea.or.kr/cms/resource/48/1945948_image2_1.jpg",
                        "http://tong.visitkorea.or.kr/cms/resource/49/1945949_image2_1.jpg"
                    ],
                    "checkin_time": "14:00",
                    "checkout_time": "13:00",
                    "benikia": False,
                    "goodstay": False,
                    "capacity": None,
                    "wished": False,
                    "category": "B02010900"
                },
                'application/json(content type id 38)': {
                    "content_id": 1128853,
                    "content_type_id": 38,
                    "title": "대전 중리전통시장",
                    "address": "대전광역시 대덕구 중리북로37번길 26",
                    "x": 128.5474558097,
                    "y": 35.8693358431,
                    "tel": "042-623-1040",
                    "image": "http://tong.visitkorea.or.kr/cms/resource/68/1946468_image2_1.jpg",
                    "wish_count": 0,
                    "images": [
                        "http://tong.visitkorea.or.kr/cms/resource/69/1946469_image2_1.jpg",
                        "http://tong.visitkorea.or.kr/cms/resource/58/2437858_image2_1.jpg"
                    ],
                    "credit_card": "가능",
                    "baby_carriage": "없음",
                    "pet": "없음",
                    "use_time": "07:00 ~ 22:00 (점포마다 다름)",
                    "rest_date": None,
                    "wished": False,
                    "category": "A04010200"
                },
                'application/json(content type id 39)': {
                    "content_id": 1927303,
                    "content_type_id": 39,
                    "title": "녹원간장게장",
                    "address": "대전광역시 유성구 대덕대로 544",
                    "x": 127.378889949,
                    "y": 36.3817008268,
                    "tel": "042-861-1697",
                    "image": None,
                    "wish_count": 0,
                    "images": [],
                    "credit_card": "가능",
                    "open_time": "11:00 ~ 21:30",
                    "rest_date": "일요일",
                    "rep_menu": "간장게장",
                    "wished": False,
                    "category": "A05020100"
                }
            }
        },
        '204': {
            'description': '세부 정보 조회 실패(해당 content id 없음)'
        }
    }
}
