package com.wecook.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "step")
public class Step {
    public enum Actions {
        MIX,
        CUT,
        SLICE,
        GRATE,
        BLEND,
        KNEAD,
        COOK,
        BOIL,
        FRY,
        ROAST,
        SEASON,
        SERVE,
        MARINATE,
        PEEL,
        CHOP,
        DRAIN,
        GARNISH,
        MELT
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "description")
    private String description;

    @Column(name = "duration")
    private int duration;

    @Enumerated(EnumType.STRING)
    @Column(name ="action")
    private Actions action;

    @Column(name = "index")
    private int index;

    @ManyToOne
    @JoinColumn(name = "recipe")
    private Recipe recipe;

    @OneToMany(mappedBy = "step", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<RecipeIngredient> ingredients = new ArrayList<>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Actions getAction() {
        return action;
    }

    public void setAction(Actions action) {
        this.action = action;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public List<RecipeIngredient> getIngredients() {
        return ingredients;
    }

    public void addIngredient(RecipeIngredient recipeIngredient) {
        this.ingredients.add(recipeIngredient);
    }

    public void removeIngredient(RecipeIngredient recipeIngredient) {
        this.ingredients.remove(recipeIngredient);
    }

}
