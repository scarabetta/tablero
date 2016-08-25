import 'usig.autocompleter.full.js';

module Home {

    let selectedOption = null;

    function afterSelection(option) {
            selectedOption = option;
            // console.log('selected option', selectedOption);
    }

    function afterGeoCoding() {
            // console.log("selected option", selectedOption);
            // console.log("selected option string", selectedOption.toString());

    }

    /*@ngInject*/
    export function usigAutocompleteDirective() {
        return {
            restrict: 'E',
            scope: {
                size: '@'
            },
            template: `<input type="text" size="40" class="form-control text" name="autocomplete" id="autocomplete" title="Ingresar dirección" ng-model="$parent.formCtrl.currentProject.direccion">`,
            link: (scope, element, attrs) => {

                /* tslint:disable:no-unused-expression */
                new usig.AutoCompleter('autocomplete', {
                    debug: true,
                    rootUrl: '../',
                    skin: 'bootstrap',
                    afterSelection: afterSelection,
                    afterGeoCoding: (pt) => afterGeoCoding()
                });
                /* tslint:enable */

            }
        };
    }

}
export = Home;
