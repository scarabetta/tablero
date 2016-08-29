module.exports = {
    production: {
      authBaseUrl: '/back/',
      captcha: {
        public: '6LdYMSUTAAAAAL83hBB-pGf9sCWB9S7S42STwgfK'
      }
    },
    development: {
        authBaseUrl: 'http://10.140.150.154:8090/proyectosBA-DS/',
        apiBaseUrl:'http://10.140.150.154:8090/proyectosBA-DS/api/',
        captcha: {
          public: '6LdYMSUTAAAAAL83hBB-pGf9sCWB9S7S42STwgfK'
        }
    },
    jsonserver: {
      authBaseUrl: 'http://localhost:3000/',
      apiBaseUrl: 'http://localhost:3000/',
        captcha: {
          public: '6LdYMSUTAAAAAL83hBB-pGf9sCWB9S7S42STwgfK'
        }
    },
    devasi: {
      authBaseUrl: '/back/',
        captcha: {
          public: '6LdYMSUTAAAAAL83hBB-pGf9sCWB9S7S42STwgfK'
        }
    },
    devrel: {
      authBaseUrl: 'http://pbarel.hexacta.com:8080/proyectosBA-DS/',
      apiBaseUrl:'http://pbarel.hexacta.com:8080/proyectosBA-DS/api/',
        captcha: {
          public: '6LdYMSUTAAAAAL83hBB-pGf9sCWB9S7S42STwgfK'
        }
    }
};
