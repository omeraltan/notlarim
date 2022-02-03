import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import NotlarComponentsPage from './notlar.page-object';
import NotlarUpdatePage from './notlar-update.page-object';
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

describe('Notlar e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let notlarComponentsPage: NotlarComponentsPage;
  let notlarUpdatePage: NotlarUpdatePage;
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
    notlarComponentsPage = new NotlarComponentsPage();
    notlarComponentsPage = await notlarComponentsPage.goToPage(navBarPage);
  });

  it('should load Notlars', async () => {
    expect(await notlarComponentsPage.title.getText()).to.match(/Notlars/);
    expect(await notlarComponentsPage.createButton.isEnabled()).to.be.true;
  });

  it('should create and delete Notlars', async () => {
    const beforeRecordsCount = (await isVisible(notlarComponentsPage.noRecords)) ? 0 : await getRecordsCount(notlarComponentsPage.table);
    notlarUpdatePage = await notlarComponentsPage.goToCreateNotlar();
    await notlarUpdatePage.enterData();
    expect(await isVisible(notlarUpdatePage.saveButton)).to.be.false;

    expect(await notlarComponentsPage.createButton.isEnabled()).to.be.true;
    await waitUntilDisplayed(notlarComponentsPage.table);
    await waitUntilCount(notlarComponentsPage.records, beforeRecordsCount + 1);
    expect(await notlarComponentsPage.records.count()).to.eq(beforeRecordsCount + 1);

    await notlarComponentsPage.deleteNotlar();
    if (beforeRecordsCount !== 0) {
      await waitUntilCount(notlarComponentsPage.records, beforeRecordsCount);
      expect(await notlarComponentsPage.records.count()).to.eq(beforeRecordsCount);
    } else {
      await waitUntilDisplayed(notlarComponentsPage.noRecords);
    }
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
