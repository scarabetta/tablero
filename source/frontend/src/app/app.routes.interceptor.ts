export default function routesInterceptor($rootScope: ng.IRootScopeService, $state: ng.ui.IStateService,
  localStorageService:angular.local.storage.ILocalStorageService) {
    $rootScope.$on('$stateChangeStart', function (event, toState, toParams) {
      var requireLogin = toState.data.requireLogin;
      if (requireLogin && !localStorageService.get('token')) {
        event.preventDefault();
        $state.go('login');
      }
    });
}
