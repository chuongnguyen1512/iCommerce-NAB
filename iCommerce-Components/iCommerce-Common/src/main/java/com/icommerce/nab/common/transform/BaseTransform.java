package com.icommerce.nab.common.transform;

/**
 * Base transformer class used to convert objects
 * @param <I> 1st object
 * @param <O> 2nd object
 */
public abstract class BaseTransform<I, O> {

    /**
     * Transform from 1st object to 2nd object
     *
     * @param object 1st object
     * @return 2nd object
     */
    public abstract O transform(I object);

    /**
     * Transform from 2nd object to 1st object
     *
     * @param object 2nd object
     * @return 1st object
     */
    public abstract I transformBack(O object);
}
