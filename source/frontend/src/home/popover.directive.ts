module Popover {

    /*@ngInject*/
    export function popoverDirective() {
        return {
            restrict: 'E',
            template: '<span class="glyphicon glyphicon-question-sign" data-toggle="popover"></span>',
            link: (scope, element, attrs) => {
                /* tslint:disable:no-unused-expression */
                (<any>$(element.children())).popover({
                    placement: attrs.placement,
                    title: attrs.title,
                    content: attrs.content
                }).click(function(e){
                    e.preventDefault();
                    return true;
                });
                /* tslint:enable */
            }
        };
    }

}
export = Popover;
