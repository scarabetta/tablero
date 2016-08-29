module Auth {

  export class AuthInterceptor {

     private static instance: AuthInterceptor;
     private tokenKey = 'token';

    /*@ngInject*/
    public static Factory($q: ng.IQService, localStorageService:angular.local.storage.ILocalStorageService) {
      if (!AuthInterceptor.instance) {
        AuthInterceptor.instance = new AuthInterceptor($q, localStorageService);
      }
      return AuthInterceptor.instance;
    }

    /*@ngInject*/
    constructor(private $q: ng.IQService, private localStorageService:angular.local.storage.ILocalStorageService) {}

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
      (<any>$).LoadingOverlay("hide");
      return response || self.$q.when(response);
    }

    public responseError(rejection) {
      var self = Auth.AuthInterceptor.instance;
      (<any>$).LoadingOverlay("hide");
      console.log(rejection.status);
      if (rejection.status === 401) {
        console.log('Error');
      }
      return self.$q.reject(rejection);
    }

  }
}

export = Auth;