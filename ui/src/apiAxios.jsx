import axios from 'axios';

const apiAxios = axios.create();

apiAxios.defaults.headers.common['Authorization'] = "token";
apiAxios.defaults.headers.common['Content-Type'] = "application/json";
apiAxios.defaults.headers.common['RequestId'] = "request-id";
apiAxios.defaults.headers.common['SessionId'] = "session-id";

export default apiAxios;
