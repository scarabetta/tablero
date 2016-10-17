const template = require('./view-project.html');
import {FormProjectComponentController} from '../form.project.component';

module Home {

    export let viewProjectComponent = {
        bindings: {
            idproject: '='
        },
        templateUrl: template,
        controller: FormProjectComponentController,
        controllerAs: 'formCtrl',
    };

}

export = Home;
