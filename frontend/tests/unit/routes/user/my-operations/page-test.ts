import { module, test } from 'qunit';
import { setupTest } from 'ember-qunit';

module('Unit | Route | user/my-operations/page', function(hooks) {
  setupTest(hooks);

  test('it exists', function(assert) {
    let route = this.owner.lookup('route:user/my-operations/page');
    assert.ok(route);
  });
});
