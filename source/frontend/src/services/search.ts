module Search {

    export class Search {

        public searchText: any;

        /*@ngInject*/
        constructor(private $http: ng.IHttpService, private $state: ng.ui.IStateService, private localStorageService:angular.local.storage.ILocalStorageService) {}

        getText(): ng.IPromise<any> {
            var searchTextObj = {
                text : ""
            };
            this.searchText = searchTextObj;
            return this.searchText;
        }


        setText(text): ng.IPromise<any> {
            var searchTextObj = {
                text : text
            };
            this.searchText = searchTextObj;
            return this.searchText;
        }

    }

}

export = Search;
