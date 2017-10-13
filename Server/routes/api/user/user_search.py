from flask_jwt import current_identity, jwt_required
from flask_restful_swagger_2 import Resource, request, swagger

from db.models.friend import FriendRequestsModel
from db.models.user import AccountModel
from routes.api.user import user_search_doc


class UserSearch(Resource):
    @swagger.doc(user_search_doc.USER_SEARCH)
    @jwt_required()
    def get(self):
        id = request.args.get('id')

        user_data = AccountModel.objects(id=id).first()
        friend_requested = True if FriendRequestsModel.objects(requester_id=current_identity, receiver_id=id) else None

        if not user_data:
            return '', 204
        else:
            return {
                'id': id,
                'email': user_data.email,
                'phone': user_data.phone,
                'name': user_data.name,
                'friend_requested': friend_requested
            }
