import math

from flask_jwt import current_identity, jwt_required
from flask_restful_swagger_2 import Resource, request, swagger

from db.models.tour_base import TourTopModel
from db.models.user import AccountModel
from routes.api.tour import tour_doc


def detect(tour_list, sort_type, x=0.0, y=0.0):
    client_wish_list = AccountModel.objects(id=current_identity).first().wish_list

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
        'content_id': tour.content_id,
        'content_type_id': tour.content_type_id,
        'title': tour.title,
        'address': tour.address,
        'category': tour.small_category,
        'image': tour.image,
        'wish_count': tour.wish_count,
        'wished': tour.id in client_wish_list,
        'x': tour.x,
        'y': tour.y
    } for tour in tour_list]


class SearchedTourList(Resource):
    @swagger.doc(tour_doc.SEARCHED_TOUR_LIST)
    @jwt_required()
    def get(self):
        keyword = request.args.get('keyword')
        sort_type = request.args.get('sort_type', type=int)

        tour_list = detect([tour for tour in TourTopModel.objects if keyword in tour.title], sort_type, request.args.get('x', type=float, default=0.0), request.args.get('y', type=float, default=0.0))
        # After keyword filtering

        if tour_list:
            return tour_list
        else:
            return '', 204


class CategorizedTourList(Resource):
    @swagger.doc(tour_doc.CATEGORIZED_TOUR_LIST)
    @jwt_required()
    def get(self):
        category = request.args.get('category')
        sort_type = request.args.get('sort_type', type=int)

        tour_list = detect([tour for tour in TourTopModel.objects if category == tour.small_category], sort_type, request.args.get('x', type=float, default=0.0), request.args.get('y', type=float, default=0.0))

        if tour_list:
            return tour_list
        else:
            return '', 204
