module Auth {

  export class AuthInterceptor {

     private static instance: AuthInterceptor;
     private tokenKey = 'token';

    /*@ngInject*/
    public static Factory($q: ng.IQService, localStorageService:angular.local.storage.ILocalStorageService, $injector) {
      if (!AuthInterceptor.instance) {
        AuthInterceptor.instance = new AuthInterceptor($q, localStorageService, $injector);
      }
      return AuthInterceptor.instance;
    }

    constructor(private $q: ng.IQService, private localStorageService:angular.local.storage.ILocalStorageService, private $injector) {}

    public request(request) {
      var self = Auth.AuthInterceptor.instance;
      var token = self.localStorageService.get(self.tokenKey);
      if (token) {
        request.headers['Authorization'] = 'Bearer ' + token; // tslint:disable-line no-string-literal
        request.headers['Access-Control-Allow-Origin'] =  '*';
        request.headers['Access-Control-Allow-Methods'] =  'PUT, GET, POST, DELETE, OPTIONS';
        request.headers['Access-Control-Allow-Headers'] = 'accept, content-type, x-parse-application-id, x-parse-rest-api-key, x-parse-session-token';
      }
      (<any>$).LoadingOverlay("show", {
        color : "rgba(255, 255, 255, 0.8)"
      });
      return request;
    }

    public response(response) {
      var self = Auth.AuthInterceptor.instance;
      if (response.headers('TokenRenovado')) {
        self.localStorageService.set(self.tokenKey, response.headers('TokenRenovado'));
      }
      (<any>$).LoadingOverlay("hide");
      return response || self.$q.when(response);
    }

    public responseError(rejection) {
      var self = Auth.AuthInterceptor.instance;
      (<any>$).LoadingOverlay("hide");
      if (rejection.status === 401 || rejection.status === 400) {
        self.$injector.get('$state').go('login');
        return self.$q.defer().promise;
      }
      return self.$q.reject(rejection);
    }

  }
}

export = Auth;
