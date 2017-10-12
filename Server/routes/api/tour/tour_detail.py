from flask_restful_swagger_2 import swagger, Resource, request
from flask_jwt import jwt_required, current_identity

from db.models.tour_base import TourTopModel
from db.models.user import AccountModel
from db.mongo_helper import *

import swagger_docs


class TourDetail(Resource):
    @swagger.doc(swagger_docs.TOUR_DETAIL)
    @jwt_required()
    def get(self):
        content_id = request.args.get('content_id', type=int)
        client_id = current_identity

        tour = mongo_to_dict(TourTopModel.objects(content_id=content_id).first(), ['views', '_cls'])
        if not tour:
            return '', 204

        tour['wished'] = tour['content_id'] in AccountModel.objects(id=client_id).first().wish_list
        tour['category'] = tour.pop('small_category')

        return tour
