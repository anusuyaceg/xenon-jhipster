import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { XenonSharedModule } from '../../shared';

import {
    XenService,
    XenPopupService,
    XenComponent,
    XenDetailComponent,
    XenDialogComponent,
    XenPopupComponent,
    XenDeletePopupComponent,
    XenDeleteDialogComponent,
    xenRoute,
    xenPopupRoute,
} from './';

let ENTITY_STATES = [
    ...xenRoute,
    ...xenPopupRoute,
];

@NgModule({
    imports: [
        XenonSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        XenComponent,
        XenDetailComponent,
        XenDialogComponent,
        XenDeleteDialogComponent,
        XenPopupComponent,
        XenDeletePopupComponent,
    ],
    entryComponents: [
        XenComponent,
        XenDialogComponent,
        XenPopupComponent,
        XenDeleteDialogComponent,
        XenDeletePopupComponent,
    ],
    providers: [
        XenService,
        XenPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class XenonXenModule {}
