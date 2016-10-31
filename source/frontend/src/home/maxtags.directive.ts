module Home {

  /*@ngInject*/
  export function maxTagsDirective() {
    return {
     require: 'ngModel',
     link: function(scope, element, attrs, ngCtrl) {
       var maxTags = attrs.maxTags ? parseInt(attrs.maxTags, 1) : null;

       ngCtrl.$parsers.push(function(value) {
         if (value && maxTags && value.length > maxTags) {
           value.splice(value.length - 1, 1);
         }
         return value;
       });
     }
   };
  }
}

export = Home;
