import math

from flask_restful_swagger_2 import swagger, Resource, request
from flask_jwt import jwt_required, current_identity

from db.models.tour import *
from db.models.user import AccountModel

import swagger_docs


def detect(tour_list, sort_type, client_id, x=0.0, y=0.0):
    client_wish_list = AccountModel.objects(id=client_id).first().wish_list

    if sort_type == 1:
        # 조회순
        tour_list = sorted(tour_list, key=lambda k: k.views, reverse=True)
    elif sort_type == 2:
        # 위시리스트 많은 순
        tour_list = sorted(tour_list, key=lambda k: k.wish_count, reverse=True)
    elif sort_type == 3:
        # 거리순
        for tour in tour_list:
            tour.distance = math.sqrt(math.pow(tour.x - x, 2) + math.pow(tour.y - y, 2))

        tour_list = sorted(tour_list, key=lambda k: k.distance)

    return [{
        'content_id': tour.id,
        'content_type_id': tour.content_type_id,
        'title': tour.title,
        'address': tour.address,
        'category': tour.small_category,
        'image': tour.img_big_url,
        'wish_count': tour.wish_count,
        'wished': tour.id in client_wish_list,
        'x': tour.x,
        'y': tour.y
    } for tour in tour_list]


class SearchedTourList(Resource):
    @swagger.doc(swagger_docs.SEARCHED_TOUR_LIST)
    @jwt_required()
    def get(self):
        keyword = request.args.get('keyword')
        sort_type = request.args.get('sort_type', type=int)
        client_id = current_identity

        tour_list = detect([tour for tour in TourTopModel.objects if keyword in tour.title], sort_type, client_id, request.args.get('x', type=float, default=0.0), request.args.get('y', type=float, default=0.0))
        # After keyword filtering

        if tour_list:
            return tour_list
        else:
            return '', 204


class CategorizedTourList(Resource):
    @swagger.doc(swagger_docs.CATEGORIZED_TOUR_LIST)
    @jwt_required()
    def get(self):
        category = request.args.get('category')
        sort_type = request.args.get('sort_type', type=int)
        client_id = current_identity

        tour_list = detect([tour for tour in TourTopModel.objects if category == tour.small_category], sort_type, client_id, request.args.get('x', type=float, default=0.0), request.args.get('y', type=float, default=0.0))

        if tour_list:
            return tour_list
        else:
            return '', 204
