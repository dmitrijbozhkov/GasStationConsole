import { module, test } from 'qunit';
import { setupTest } from 'ember-qunit';

module('Unit | Route | user/manage-fuel', function(hooks) {
  setupTest(hooks);

  test('it exists', function(assert) {
    let route = this.owner.lookup('route:user/manage-fuel');
    assert.ok(route);
  });
});
