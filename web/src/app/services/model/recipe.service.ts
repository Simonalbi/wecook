import { HttpClient, HttpHeaders } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Recipe } from '../../model/Recipe.model';
import { map, Observable } from 'rxjs';
import { plainToInstance } from 'class-transformer';
import { Step } from '../../model/Step.model';
import { RecipeIngredient } from '../../model/RecipeIngredient.model';
import { AuthService } from '../auth.service';

@Injectable({
  providedIn: 'root'
})
export class RecipeService {
  private readonly URL: string = 'http://localhost:8080/wecook/recipe';
  
  private http = inject(HttpClient);
  private readonly authService = inject(AuthService);

  constructor() {}

  public post(
    data: {
      title: string,
      description: string,
      difficulty: string,
      category: string
    }
  ): Observable<Recipe> {
    const headers = new HttpHeaders({
      Authorization: `Bearer ${this.authService.getToken()}`
    });
    
    return this.http.post<Recipe>(this.URL, data, { headers: headers }).pipe(
      map((response) => plainToInstance(Recipe, response))
    );
  }

  public get(
    recipeId: number
  ): Observable<Recipe> {
    const headers = new HttpHeaders({
      Authorization: `Bearer ${this.authService.getToken()}`
    });

    return this.http.get<Recipe>(`${this.URL}/${recipeId}`, { headers: headers }).pipe(
      map((response) => {
        const recipe = plainToInstance(Recipe, response);
        recipe.steps = recipe.steps.map((s) => {
          const step = plainToInstance(Step, s);
          step.ingredients = step.ingredients.map((i) => plainToInstance(RecipeIngredient, i));
          return step;
        });
        return recipe;
      })
    );
  }

  public getAll(): Observable<Array<Recipe>> {
    const headers = new HttpHeaders({
      Authorization: `Bearer ${this.authService.getToken()}`
    });

    return this.http.get<Array<Recipe>>(this.URL, { headers: headers }).pipe(
      map((response) => response.map((r) => {
        const recipe = plainToInstance(Recipe, r);
        recipe.steps = recipe.steps.map((s) => {
          const step = plainToInstance(Step, s);
          step.ingredients = step.ingredients.map((i) => plainToInstance(RecipeIngredient, i));
          return step;
        });
        return recipe;
      })
    ));
  }

  public patch(
    recipeId: number,
    data: Partial<{
      title: string,
      description: string,
      difficulty: string,
      category: string
    }>
  ): Observable<Recipe> {
    const headers = new HttpHeaders({
      Authorization: `Bearer ${this.authService.getToken()}`
    });

    return this.http.patch<Recipe>(`${this.URL}/${recipeId}`, data, { headers: headers }).pipe(
      map((response) => {
        const recipe = plainToInstance(Recipe, response);
        recipe.steps = recipe.steps.map((step) => plainToInstance(Step, step));
        return recipe;
      })
    );
  }

  public delete(
    recipeId: number
  ): Observable<void> {
    const headers = new HttpHeaders({
      Authorization: `Bearer ${this.authService.getToken()}`
    });

    return this.http.delete<void>(`${this.URL}/${recipeId}`, { headers: headers });
  }
}
