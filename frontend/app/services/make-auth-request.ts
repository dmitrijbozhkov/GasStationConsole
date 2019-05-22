import Service from '@ember/service';
import { inject } from '@ember/service';
import { fetchAjax } from "./service-commons"

export default class MakeAuthRequest extends Service.extend({
  session: inject("session" as any)
}) {
  ajaxRequest(params: JQuery.AjaxSettings) {
    if (!params.headers) {
      params.headers = {};
    }
    params.headers["Authorization"] = "Bearer " + this.get("session.data.authenticated.token" as any);
    return fetchAjax(params);
  }
}

// DO NOT DELETE: this is how TypeScript knows how to look up your services.
declare module '@ember/service' {
  interface Registry {
    'make-auth-request': MakeAuthRequest;
  }
}
