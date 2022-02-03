import { element, by, ElementFinder } from 'protractor';
import { waitUntilDisplayed, waitUntilHidden, isVisible } from '../../util/utils';

const expect = chai.expect;

export default class PersonelTanimUpdatePage {
  pageTitle: ElementFinder = element(by.id('notlarimApp.personelTanim.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  adiInput: ElementFinder = element(by.css('input#personel-tanim-adi'));
  soyadiInput: ElementFinder = element(by.css('input#personel-tanim-soyadi'));
  gorevtipSelect: ElementFinder = element(by.css('select#personel-tanim-gorevtip'));

  getPageTitle() {
    return this.pageTitle;
  }

  async setAdiInput(adi) {
    await this.adiInput.sendKeys(adi);
  }

  async getAdiInput() {
    return this.adiInput.getAttribute('value');
  }

  async setSoyadiInput(soyadi) {
    await this.soyadiInput.sendKeys(soyadi);
  }

  async getSoyadiInput() {
    return this.soyadiInput.getAttribute('value');
  }

  async setGorevtipSelect(gorevtip) {
    await this.gorevtipSelect.sendKeys(gorevtip);
  }

  async getGorevtipSelect() {
    return this.gorevtipSelect.element(by.css('option:checked')).getText();
  }

  async gorevtipSelectLastOption() {
    await this.gorevtipSelect.all(by.tagName('option')).last().click();
  }
  async save() {
    await this.saveButton.click();
  }

  async cancel() {
    await this.cancelButton.click();
  }

  getSaveButton() {
    return this.saveButton;
  }

  async enterData() {
    await waitUntilDisplayed(this.saveButton);
    await this.setAdiInput('adi');
    await waitUntilDisplayed(this.saveButton);
    await this.setSoyadiInput('soyadi');
    await waitUntilDisplayed(this.saveButton);
    await this.gorevtipSelectLastOption();
    await this.save();
    await waitUntilHidden(this.saveButton);
  }
}
