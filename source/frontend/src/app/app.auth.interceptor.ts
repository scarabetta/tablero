import {AuthInterceptor} from '../interceptors/auth.interceptor';

export default function routesInterceptor($httpProvider:ng.IHttpProvider) {
  $httpProvider.interceptors.push(AuthInterceptor.Factory);
}
