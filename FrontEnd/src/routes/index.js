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
    }, {
        path: '/catalogue/:id',
        name: 'profile',
        component: ProductView,
        props: true,
    },
    , {
        path: '/catalogue',
        name: 'catalogue',
        component: CatalogueView,
        props: true,
    },
    {
        path: '/:catchAll(.*)',
        name: 'not-found',
        component: NotFoundView,
    }

]

const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes
})

export default router
