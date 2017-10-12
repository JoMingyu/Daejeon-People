from flask_restful_swagger_2 import swagger, Resource, request


class UserData(Resource):
    @swagger.doc()
    def get(self):
        pass
