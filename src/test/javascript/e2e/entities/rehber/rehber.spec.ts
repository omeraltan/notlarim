import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import RehberComponentsPage from './rehber.page-object';
import RehberUpdatePage from './rehber-update.page-object';
import {
  waitUntilDisplayed,
  waitUntilAnyDisplayed,
  click,
  getRecordsCount,
  waitUntilHidden,
  waitUntilCount,
  isVisible,
} from '../../util/utils';

const expect = chai.expect;

describe('Rehber e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let rehberComponentsPage: RehberComponentsPage;
  let rehberUpdatePage: RehberUpdatePage;
  const username = process.env.E2E_USERNAME ?? 'admin';
  const password = process.env.E2E_PASSWORD ?? 'admin';

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.waitUntilDisplayed();
    await signInPage.username.sendKeys(username);
    await signInPage.password.sendKeys(password);
    await signInPage.loginButton.click();
    await signInPage.waitUntilHidden();
    await waitUntilDisplayed(navBarPage.entityMenu);
    await waitUntilDisplayed(navBarPage.adminMenu);
    await waitUntilDisplayed(navBarPage.accountMenu);
  });

  beforeEach(async () => {
    await browser.get('/');
    await waitUntilDisplayed(navBarPage.entityMenu);
    rehberComponentsPage = new RehberComponentsPage();
    rehberComponentsPage = await rehberComponentsPage.goToPage(navBarPage);
  });

  it('should load Rehbers', async () => {
    expect(await rehberComponentsPage.title.getText()).to.match(/Rehbers/);
    expect(await rehberComponentsPage.createButton.isEnabled()).to.be.true;
  });

  it('should create and delete Rehbers', async () => {
    const beforeRecordsCount = (await isVisible(rehberComponentsPage.noRecords)) ? 0 : await getRecordsCount(rehberComponentsPage.table);
    rehberUpdatePage = await rehberComponentsPage.goToCreateRehber();
    await rehberUpdatePage.enterData();
    expect(await isVisible(rehberUpdatePage.saveButton)).to.be.false;

    expect(await rehberComponentsPage.createButton.isEnabled()).to.be.true;
    await waitUntilDisplayed(rehberComponentsPage.table);
    await waitUntilCount(rehberComponentsPage.records, beforeRecordsCount + 1);
    expect(await rehberComponentsPage.records.count()).to.eq(beforeRecordsCount + 1);

    await rehberComponentsPage.deleteRehber();
    if (beforeRecordsCount !== 0) {
      await waitUntilCount(rehberComponentsPage.records, beforeRecordsCount);
      expect(await rehberComponentsPage.records.count()).to.eq(beforeRecordsCount);
    } else {
      await waitUntilDisplayed(rehberComponentsPage.noRecords);
    }
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
