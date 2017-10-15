from flask_jwt import current_identity, jwt_required
from flask_restful_swagger_2 import Resource, request, swagger

from db.models.tour_base import TourTopModel
from db.models.user import AccountModel
from db.mongo_helper import *
from routes.api.tour import tour_doc


class TourDetail(Resource):
    @swagger.doc(tour_doc.TOUR_DETAIL)
    @jwt_required()
    def get(self):
        content_id = request.args.get('content_id', type=int)

        tour = mongo_to_dict(TourTopModel.objects(content_id=content_id).first(), ['views', '_cls'])
        if not tour:
            return '', 204

        tour['wished'] = tour['content_id'] in AccountModel.objects(id=current_identity).first().wish_list
        tour['category'] = tour.pop('small_category')

        return tour
