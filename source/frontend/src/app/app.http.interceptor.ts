import {AuthInterceptor} from '../interceptors/auth.interceptor';
import {ErrorsInterceptor} from '../interceptors/errors.interceptor';

export default function routesInterceptor($httpProvider:ng.IHttpProvider) {
  $httpProvider.interceptors.push(ErrorsInterceptor.Factory);
  $httpProvider.interceptors.push(AuthInterceptor.Factory);
}
