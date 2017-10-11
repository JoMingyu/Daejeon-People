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
