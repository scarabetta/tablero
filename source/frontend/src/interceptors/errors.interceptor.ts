module Errors {

  export class ErrorsInterceptor {

     private static instance: ErrorsInterceptor;

    /*@ngInject*/
    public static Factory($q: ng.IQService, localStorageService:angular.local.storage.ILocalStorageService, $injector) {
      if (!ErrorsInterceptor.instance) {
        ErrorsInterceptor.instance = new ErrorsInterceptor($q, localStorageService, $injector);
      }
      return ErrorsInterceptor.instance;
    }

    constructor(private $q: ng.IQService, private localStorageService:angular.local.storage.ILocalStorageService, private $injector) {}

    public request(request) {
      return request;
    }

    public response(response) {
      var self = Errors.ErrorsInterceptor.instance;
      return response || self.$q.when(response);
    }

    public responseError(rejection) {
      var self = Errors.ErrorsInterceptor.instance;
      if (self.$injector.get('$state').current.name !== 'login') {
        if (rejection.data.mensajeError) {
          (<any>$('#errorModal .modal-body')).html('<p>' + rejection.data.mensajeError + '</p>');
        } else {
          (<any>$('#errorModal .modal-body')).html('<p>Ha ocurrido un error inesperado, verificá los datos ingresados e intentá nuevamente</p>');
        }
        (<any>$('#errorModal')).modal('show');
        return self.$q.reject(rejection);
      }
    }

  }
}

export = Errors;
