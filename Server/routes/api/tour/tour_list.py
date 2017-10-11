from flask_restful_swagger_2 import swagger, Resource, request

from db.models.tour import *


def detect(tour_list, sort_type):
    print(tour_list)
    if sort_type == 1:
        # 조회순
        tour_list = sorted(tour_list, key=lambda k: k.views, reverse=True)
    elif sort_type == 2:
        # 위시리스트 많은 순
        tour_list = sorted(tour_list, key=lambda k: k.wish_count, reverse=True)
    elif sort_type == 3:
        # 거리순
        pass

    return [{
        'content_id': tour.id,
        'content_type_id': tour.content_type_id,
        'title': tour.title,
        'address': tour.address,
        'category': tour.small_category,
        'image': tour.img_big_url,
        'x': tour.x,
        'y': tour.y
    } for tour in tour_list]


class SearchedTourList(Resource):
    def get(self):
        keyword = request.args.get('keyword')
        sort_type = request.args.get('sort_type', type=int)

        tour_list = detect([tour for tour in TourTopModel.objects if keyword in tour.title], sort_type)

        return tour_list


class CategorizedTourList(Resource):
    def get(self):
        pass
