from flask_jwt import jwt_required, current_identity
from flask_restful_swagger_2 import swagger, Resource, request

from db.models.tour_base import TourTopModel
from db.models.user import AccountModel
from routes.api.user.doc import wish_doc


class WishList(Resource):
    @swagger.doc(wish_doc.WISH_ADD)
    @jwt_required()
    def post(self):
        content_id = request.form.get('content_id', type=int)
        client_id = current_identity

        if not TourTopModel.objects(content_id=content_id)\
                or content_id in AccountModel.objects(id=client_id).first().wish_list:
            # Content id does not exist, or Already in wish list
            return '', 200
        else:
            wish_list = list(AccountModel.objects(id=client_id).first().wish_list)
            wish_list.append(content_id)

            AccountModel.objects(id=client_id).first().update(wish_list=wish_list)

            return '', 201

    @swagger.doc(wish_doc.WISH_GET)
    @jwt_required()
    def get(self):
        client_id = current_identity

        return list(AccountModel.objects(id=client_id).first().wish_list), 200

    @swagger.doc(wish_doc.WISH_DELETE)
    @jwt_required()
    def delete(self):
        client_id = current_identity
        content_id = request.form.get('content_id', type=int)

        wish_list = list(set(list(AccountModel.objects(id=client_id).first().wish_list)))
        del wish_list[wish_list.index(content_id)]
        AccountModel.objects(id=client_id).first().update(wish_list=wish_list)

        return '', 200
