/*@ngInject*/
export default function localStorageConfig(localStorageServiceProvider: angular.local.storage.ILocalStorageServiceProvider) {
    localStorageServiceProvider
        .setPrefix('BA')
        .setStorageType('localStorage')
        .setNotify(true, true);
}
