module.exports = {
    production: {
      authBaseUrl: '/back/',
      captcha: {
        public: '6LdYMSUTAAAAAL83hBB-pGf9sCWB9S7S42STwgfK'
      }
    },
    development: {
        authBaseUrl: 'http://10.30.10.211:8080/proyectosBA-DS/',
        apiBaseUrl:'http://10.30.10.211:8080/proyectosBA-DS/api/',
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
      authBaseUrl: '/back/',
        captcha: {
          public: '6LdYMSUTAAAAAL83hBB-pGf9sCWB9S7S42STwgfK'
        }
    }
};
