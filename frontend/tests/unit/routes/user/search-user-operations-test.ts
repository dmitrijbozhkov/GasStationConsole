import { module, test } from 'qunit';
import { setupTest } from 'ember-qunit';

module('Unit | Route | user/search-user-operations', function(hooks) {
  setupTest(hooks);

  test('it exists', function(assert) {
    let route = this.owner.lookup('route:user/search-user-operations');
    assert.ok(route);
  });
});
