import { module, test } from 'qunit';
import { setupTest } from 'ember-qunit';

module('Unit | Route | user/search-operations', function(hooks) {
  setupTest(hooks);

  test('it exists', function(assert) {
    let route = this.owner.lookup('route:user/search-operations');
    assert.ok(route);
  });
});
