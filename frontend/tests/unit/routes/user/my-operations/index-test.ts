import { module, test } from 'qunit';
import { setupTest } from 'ember-qunit';

module('Unit | Route | user/my-operations/index', function(hooks) {
  setupTest(hooks);

  test('it exists', function(assert) {
    let route = this.owner.lookup('route:user/my-operations/index');
    assert.ok(route);
  });
});