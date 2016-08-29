const template = require('./alertmodal.html');

module Home {

  export class AlertController {

    private title: string;
    private text: string;
    private callback: any;

    /*@ngInject*/
    constructor() {
      (<any>$('#alertModalId')).modal('hide');
      console.log(this.title);
      console.log(this.text);
    }

    acept() {
      (<any>$('#alertModalId')).modal('hide');
      this.callback();
    }

  }

  export let alertComponent = {
      bindings: {
          title: '@',
          text: '@',
          callback: '&'
      },
      templateUrl: template,
      controller: AlertController,
      controllerAs: 'alertCtrl',
  };
}

export = Home;
