import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import NotBaslikTanimComponentsPage from './not-baslik-tanim.page-object';
import NotBaslikTanimUpdatePage from './not-baslik-tanim-update.page-object';
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

describe('NotBaslikTanim e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let notBaslikTanimComponentsPage: NotBaslikTanimComponentsPage;
  let notBaslikTanimUpdatePage: NotBaslikTanimUpdatePage;
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
    notBaslikTanimComponentsPage = new NotBaslikTanimComponentsPage();
    notBaslikTanimComponentsPage = await notBaslikTanimComponentsPage.goToPage(navBarPage);
  });

  it('should load NotBaslikTanims', async () => {
    expect(await notBaslikTanimComponentsPage.title.getText()).to.match(/Not Baslik Tanims/);
    expect(await notBaslikTanimComponentsPage.createButton.isEnabled()).to.be.true;
  });

  it('should create and delete NotBaslikTanims', async () => {
    const beforeRecordsCount = (await isVisible(notBaslikTanimComponentsPage.noRecords))
      ? 0
      : await getRecordsCount(notBaslikTanimComponentsPage.table);
    notBaslikTanimUpdatePage = await notBaslikTanimComponentsPage.goToCreateNotBaslikTanim();
    await notBaslikTanimUpdatePage.enterData();
    expect(await isVisible(notBaslikTanimUpdatePage.saveButton)).to.be.false;

    expect(await notBaslikTanimComponentsPage.createButton.isEnabled()).to.be.true;
    await waitUntilDisplayed(notBaslikTanimComponentsPage.table);
    await waitUntilCount(notBaslikTanimComponentsPage.records, beforeRecordsCount + 1);
    expect(await notBaslikTanimComponentsPage.records.count()).to.eq(beforeRecordsCount + 1);

    await notBaslikTanimComponentsPage.deleteNotBaslikTanim();
    if (beforeRecordsCount !== 0) {
      await waitUntilCount(notBaslikTanimComponentsPage.records, beforeRecordsCount);
      expect(await notBaslikTanimComponentsPage.records.count()).to.eq(beforeRecordsCount);
    } else {
      await waitUntilDisplayed(notBaslikTanimComponentsPage.noRecords);
    }
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
