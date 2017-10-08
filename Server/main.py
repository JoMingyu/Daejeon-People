from flask import Flask
from flask_jwt import JWT
from flask_cors import CORS
from server_preprocessor import decorate, add_resources


def create_app():
    """
    Creates Flask instance & initialize

    Flask best practice : Application Factories
    - Use 'application factory', 'current_app'

    # What's the big deal?
    1. Testing
    2. Multiple instances running in one process
    3. from app import app -> circular imports

    :return: app
    :rtype: Flask
    """
    app = Flask(__name__)

    app.config['SECRET_KEY'] = '!owQzm[pn;?11K'
    app.config['JWT_AUTH_URL_RULE'] = '/signin'
    app.config['JWT_AUTH_USERNAME_KEY'] = 'id'
    app.config['JWT_AUTH_PASSWORD_KEY'] = 'pw'

    CORS(app)
    # Support AJAX & Swagger API
    decorate(app)
    add_resources(app, api_version=0.1)

    return app

_app = create_app()


@_app.route('/test')
def index():
    return 'hello?'

if __name__ == '__main__':
    _app.run(debug=True)
