import { Routes } from '@angular/router';

import { authGuard } from './guards/auth.guard';
import { AuthComponent } from './auth/auth.component';
import { ErrorComponent } from './error/error.component';
import { HomeComponent } from './pages/home/home.component';
import { NewPostComponent } from './pages/new-post/new-post.component';
import { ProfileComponent } from './pages/profile/profile.component';
import { SavedPostsComponent } from './pages/saved-posts/saved-posts.component';
import { RecipeComponent } from './pages/recipe/recipe.component';
import { SearchComponent } from './pages/search/search.component';

export const routes: Routes = [
  {
    path: 'login',
    component: AuthComponent,
  },
  {
    path: 'home',
    component: HomeComponent,
    canActivate: [authGuard],
  },
  {
    path: 'new-post',
    component: NewPostComponent,
    canActivate: [authGuard],
  },
  {
    path: 'recipe/:postId',
    component: RecipeComponent,
    canActivate: [authGuard]
  },
  {
    path: 'search',
    component: SearchComponent,
    canActivate: [authGuard],
  },
  {
    path: 'saved-posts',
    component: SavedPostsComponent,
    canActivate: [authGuard]
  },
  {
    path: 'profile/:userId',
    component: ProfileComponent,
    canActivate: [authGuard],
  },
  {
    path: 'error',
    component: ErrorComponent,
  },
  {
    path: '',
    redirectTo: '/home',
    pathMatch: 'full'
  },
  {
    path: '**',
    component: ErrorComponent,
    data: { statusCode: '404' }
  }
];
