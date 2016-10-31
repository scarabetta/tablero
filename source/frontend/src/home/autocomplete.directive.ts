import 'usig.autocompleter.full.js';

module Home {

    function afterSelection(option) {
            (<any>$("#autocomplete")).val(option.toString());
            angular.element((<any>$("#autocomplete"))).triggerHandler('input');
    }

    /*@ngInject*/
    export function usigAutocompleteDirective() {
        return {
            restrict: 'E',
            scope: {
                size: '@',
                model: '=',
                idinput: "@"
            },
            template: `<input type="text" size="40" class="form-control text" name="idinput" id="idinput" title="Ingresar dirección" ng-model="model">`,
            link: (scope, element, attrs) => {
              var id = attrs.idinput;
                /* tslint:disable:no-unused-expression */
                new usig.AutoCompleter(id, {
                    debug: true,
                    rootUrl: '../',
                    skin: 'bootstrap',
                    afterSelection: afterSelection
                });
                /* tslint:enable */

            }
        };
    }

}
export = Home;
