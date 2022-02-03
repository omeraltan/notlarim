import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import PersonelTanimComponentsPage from './personel-tanim.page-object';
import PersonelTanimUpdatePage from './personel-tanim-update.page-object';
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

describe('PersonelTanim e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let personelTanimComponentsPage: PersonelTanimComponentsPage;
  let personelTanimUpdatePage: PersonelTanimUpdatePage;
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
    personelTanimComponentsPage = new PersonelTanimComponentsPage();
    personelTanimComponentsPage = await personelTanimComponentsPage.goToPage(navBarPage);
  });

  it('should load PersonelTanims', async () => {
    expect(await personelTanimComponentsPage.title.getText()).to.match(/Personel Tanims/);
    expect(await personelTanimComponentsPage.createButton.isEnabled()).to.be.true;
  });

  it('should create and delete PersonelTanims', async () => {
    const beforeRecordsCount = (await isVisible(personelTanimComponentsPage.noRecords))
      ? 0
      : await getRecordsCount(personelTanimComponentsPage.table);
    personelTanimUpdatePage = await personelTanimComponentsPage.goToCreatePersonelTanim();
    await personelTanimUpdatePage.enterData();
    expect(await isVisible(personelTanimUpdatePage.saveButton)).to.be.false;

    expect(await personelTanimComponentsPage.createButton.isEnabled()).to.be.true;
    await waitUntilDisplayed(personelTanimComponentsPage.table);
    await waitUntilCount(personelTanimComponentsPage.records, beforeRecordsCount + 1);
    expect(await personelTanimComponentsPage.records.count()).to.eq(beforeRecordsCount + 1);

    await personelTanimComponentsPage.deletePersonelTanim();
    if (beforeRecordsCount !== 0) {
      await waitUntilCount(personelTanimComponentsPage.records, beforeRecordsCount);
      expect(await personelTanimComponentsPage.records.count()).to.eq(beforeRecordsCount);
    } else {
      await waitUntilDisplayed(personelTanimComponentsPage.noRecords);
    }
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
