import Route from '@ember/routing/route';
import authenticatedBuyerMixin from 'frontend/mixins/authenticated-buyer-mixin';
import { inject } from '@ember/service';

// @ts-ignore
export default class User extends Route.extend(authenticatedBuyerMixin, {
    session: inject("session" as any)
}) {
    model() {
        return {
            userRole: this.get("session.claims.scopes")[0]
        };
    }
}
