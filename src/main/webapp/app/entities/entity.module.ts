import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { XenonCategoryModule } from './category/category.module';
import { XenonXenModule } from './xen/xen.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        XenonCategoryModule,
        XenonXenModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class XenonEntityModule {}
