from flask_restful_swagger_2 import swagger, Resource, request

from db.models.user import AccountModel
from routes.api.user.doc import user_search_doc


class UserSearch(Resource):
    @swagger.doc(user_search_doc.USER_SEARCH)
    def get(self):
        id = request.args.get('id')

        user_data = AccountModel.objects(id=id).first()

        if not user_data:
            return '', 204
        else:
            return {
                'id': id,
                'email': user_data.email,
                'phone': user_data.phone,
                'name': user_data.name
            }
