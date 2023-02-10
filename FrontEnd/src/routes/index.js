import {createRouter, createWebHistory} from 'vue-router'
import IndexView from "@/views/IndexView.vue";
import NotFoundView from "@/views/NotFoundView.vue";
import CatalogueView from "@/views/CatalogueView.vue";
import ProductView from "@/views/ProductView.vue";


// eslint-disable-next-line no-sparse-arrays
const routes = [
    {
        path: '/',
        name: 'index',
        component: IndexView,
        meta: {title: 'LifeLogger'}
    }, {
        path: '/catalogue/:id',
        name: 'profile',
        component: ProductView,
        props: true,
        meta: {title: 'Product - LifeLogger'}
    },
    , {
        path: '/catalogue',
        name: 'catalogue',
        component: CatalogueView,
        props: true,
        meta: {title: 'Catalogue - LifeLogger'}
    },
    {
        path: '/:catchAll(.*)',
        name: 'not-found',
        component: NotFoundView,
        meta: {title: '404 - LifeLogger'}
    }

]

const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes
})

export default router
