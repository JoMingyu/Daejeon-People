from flask_jwt import current_identity, jwt_required
from flask_restful_swagger_2 import Resource, request, swagger

from db.models.tour_base import TourTopModel
from db.models.user import AccountModel
from routes.api.user import wish_doc


class WishList(Resource):
    @swagger.doc(wish_doc.WISH_ADD)
    @jwt_required()
    def post(self):
        content_id = request.form.get('content_id', type=int)

        if not TourTopModel.objects(content_id=content_id)\
                or content_id in AccountModel.objects(id=current_identity).first().wish_list:
            # Content id does not exist, or Already in wish list
            return '', 200
        else:
            wish_list = list(AccountModel.objects(id=current_identity).first().wish_list)
            wish_list.append(content_id)

            AccountModel.objects(id=current_identity).first().update(wish_list=wish_list)

            return '', 201

    @swagger.doc(wish_doc.WISH_GET)
    @jwt_required()
    def get(self):

        return list(AccountModel.objects(id=current_identity).first().wish_list), 200

    @swagger.doc(wish_doc.WISH_DELETE)
    @jwt_required()
    def delete(self):
        content_id = request.form.get('content_id', type=int)

        wish_list = list(set(list(AccountModel.objects(id=current_identity).first().wish_list)))
        del wish_list[wish_list.index(content_id)]
        AccountModel.objects(id=current_identity).first().update(wish_list=wish_list)

        return '', 200
