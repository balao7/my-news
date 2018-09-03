# Things to improve
1. Pagination
2. Assumptions made here: Sort order by "publishedAt" is desired + we want to prevent articles with the same titles showing that I care and I can take care of the ordering
- Depending on the context and product requirements these assumptions might not hold e.g. relevancy is more desirable. Attempt to prevent duplicates by titles slows down DB write operation while yielding little benefit (many news from many different sources are produced so quickly it doesn't really matter if are a couple of duplicates in the stream)
3. For a news reader like this, it might make sense to only keep a fixed amount of articles at all times in local DB. This will make it very easy to retain all article list in the Activity's state on onSaveInstanceState() or libraries like Icepick, creating great user experience for device rotation. When to clear the local DB? When user has read the last article or it's a new day compared to the article cache.
4. UI tests with Espresso
5. AutoValue for model classes
6. GreenDao is selected for simplicity here. Each SQLite solution has pros and cons which must be carefully evaluated base on the specific use cases and requirements